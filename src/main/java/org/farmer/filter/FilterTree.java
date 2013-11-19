package org.farmer.filter;

/**
 * Created with IntelliJ IDEA.
 * User: gzy
 * Date: 13-11-18
 * Time: 上午9:13
 * To change this template use File | Settings | File Templates.
 */

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterBase;
import org.apache.hadoop.hbase.io.HbaseObjectWritable;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

//import org.farmer.parser.CommanderDispatcher;

//import org.apache.hadoop.hbase.Cell;
//import org.apache.hadoop.hbase.KeyValueUtil;
//import org.apache.hadoop.hbase.exceptions.DeserializationException;
//import org.apache.hadoop.hbase.protobuf.generated.FilterProtos;


/**
 * Implementation of {@link org.apache.hadoop.hbase.filter.Filter} that represents an ordered List of Filters
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class FilterTree implements Filter {
    /** set operator
    public static enum Operator {
        // !AND
        MUST_PASS_ALL,
        // !OR
        MUST_PASS_ONE
    }*/
    private CCJSqlParserManager parserManager = new CCJSqlParserManager();
    private Select select;// = (Select)parserManager.parse(new StringReader("select * from test where a=1 and b=2 and c=3 and (d=4 or e=5 )and f=7"));
    private PlainSelect plainSelect;// = (PlainSelect) select.getSelectBody();
    private BinaryExpression expression; //= (BinaryExpression)plainSelect.getWhere();
    private String mysql = null;
    private static final Configuration conf = HBaseConfiguration.create();
    private static final int MAX_LOG_FILTERS = 5;
   // private Operator operator = Operator.MUST_PASS_ALL;
    private List<Filter> filters = new ArrayList<Filter>();

    /**
     * Default constructor, filters nothing. Required though for RPC
     * deserialization.

    public FilterTree() {
        super();
    }
     */

    public FilterTree(final String sql) {
        this.mysql = sql;
        Select select = null;
        try {
            select = (Select)parserManager.parse(new StringReader(mysql));
            PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
            BinaryExpression expression = (BinaryExpression)plainSelect.getWhere();
        } catch (JSQLParserException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    @Override
    public void reset() {
        BinaryExpression expression = (BinaryExpression)plainSelect.getWhere();
    }

    @Override
    public boolean filterRowKey(byte[] rowKey, int offset, int length) {
        /*
        for (Filter filter : filters) {
            if (this.operator == Operator.MUST_PASS_ALL) {
                if (filter.filterAllRemaining() ||
                        filter.filterRowKey(rowKey, offset, length)) {
                    return true;
                }
            } else if (this.operator == Operator.MUST_PASS_ONE) {
                if (!filter.filterAllRemaining() &&
                        !filter.filterRowKey(rowKey, offset, length)) {
                    return false;
                }
            }
        }
        return this.operator == Operator.MUST_PASS_ONE;
        */
       /*
       *  RowFilter PrefixFilter
       * */
        return true;
    }

    @Override
    public ReturnCode filterKeyValue(KeyValue v) {
        /*
        ReturnCode rc = operator == Operator.MUST_PASS_ONE?
                ReturnCode.SKIP: ReturnCode.INCLUDE;
        for (Filter filter : filters) {
            if (operator == Operator.MUST_PASS_ALL) {
                if (filter.filterAllRemaining()) {
                    return ReturnCode.NEXT_ROW;
                }
                ReturnCode code = filter.filterKeyValue(v);
                switch (code) {
                    // Override INCLUDE and continue to evaluate.
                    case INCLUDE_AND_NEXT_COL:
                        rc = ReturnCode.INCLUDE_AND_NEXT_COL;
                    case INCLUDE:
                        continue;
                    default:
                        return code;
                }
            } else if (operator == Operator.MUST_PASS_ONE) {
                if (filter.filterAllRemaining()) {
                    continue;
                }

                switch (filter.filterKeyValue(v)) {
                    case INCLUDE:
                        if (rc != ReturnCode.INCLUDE_AND_NEXT_COL) {
                            rc = ReturnCode.INCLUDE;
                        }
                        break;
                    case INCLUDE_AND_NEXT_COL:
                        rc = ReturnCode.INCLUDE_AND_NEXT_COL;
                        // must continue here to evaluate all filters
                    case NEXT_ROW:
                    case SKIP:
                        // continue;
                }
            }
        }
        return rc;
        */
        return  ReturnCode.INCLUDE;
    }

    @Override
    public boolean filterRow() {
        /*
        for (Filter filter : filters) {
            if (operator == Operator.MUST_PASS_ALL) {
                if (filter.filterRow()) {
                    return true;
                }
            } else if (operator == Operator.MUST_PASS_ONE) {
                if (!filter.filterRow()) {
                    return false;
                }
            }
        }
        return  operator == Operator.MUST_PASS_ONE;
        */
        return true;
    }

    @Override
    public boolean filterAllRemaining() {
        /*
        for (Filter filter : filters) {
            if (filter.filterAllRemaining()) {
                if (operator == Operator.MUST_PASS_ALL) {
                    return true;
                }
            } else {
                if (operator == Operator.MUST_PASS_ONE) {
                    return false;
                }
            }
        }
        return operator == Operator.MUST_PASS_ONE;
        */
        return true;
    }

    @Override
    public KeyValue transform(KeyValue v) {
        KeyValue current = v;
        for (Filter filter : filters) {
            current = filter.transform(current);
        }
        return current;
    }



    @Override
    public void filterRow(List<KeyValue> kvs) {
        for (Filter filter : filters) {
            filter.filterRow(kvs);
        }
    }

    @Override
    public boolean hasFilterRow() {
        for (Filter filter : filters) {
            if(filter.hasFilterRow()) {
                return true;
            }
        }
        return false;
    }



    public void readFields(final DataInput in) throws IOException {
        byte opByte = in.readByte();
      //  operator = Operator.values()[opByte];
        int size = in.readInt();
        if (size > 0) {
            filters = new ArrayList<Filter>(size);
            for (int i = 0; i < size; i++) {
                Filter filter = (Filter)HbaseObjectWritable.readObject(in, conf);
                filters.add(filter);
            }
        }
    }

    public void write(final DataOutput out) throws IOException {
     //   out.writeByte(operator.ordinal());
        out.writeInt(filters.size());
        for (Filter filter : filters) {
            HbaseObjectWritable.writeObject(out, filter, Writable.class, conf);
        }
    }

    @Override
    public KeyValue getNextKeyHint(KeyValue currentKV) {
        KeyValue keyHint = null;
        /*
        for (Filter filter : filters) {
            KeyValue curKeyHint = filter.getNextKeyHint(currentKV);
            if (curKeyHint == null && operator == Operator.MUST_PASS_ONE) {
                // If we ever don't have a hint and this is must-pass-one, then no hint
                return null;
            }
            if (curKeyHint != null) {
                // If this is the first hint we find, set it
                if (keyHint == null) {
                    keyHint = curKeyHint;
                    continue;
                }
                // There is an existing hint
                if (operator == Operator.MUST_PASS_ALL &&
                        KeyValue.COMPARATOR.compare(keyHint, curKeyHint) < 0) {
                    // If all conditions must pass, we can keep the max hint
                    keyHint = curKeyHint;
                } else if (operator == Operator.MUST_PASS_ONE &&
                        KeyValue.COMPARATOR.compare(keyHint, curKeyHint) > 0) {
                    // If any condition can pass, we need to keep the min hint
                    keyHint = curKeyHint;
                }
            }
        }
        */
        return keyHint;
    }

    public boolean isFamilyEssential(byte[] name) {
        for (Filter filter : filters) {
            if (FilterBase.isFamilyEssential(filter, name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return toString(MAX_LOG_FILTERS);
    }

    protected String toString(int maxFilters) {
        int endIndex = this.filters.size() < maxFilters
                ? this.filters.size() : maxFilters;
        return String.format("%s %s (%d/%d): %s",
                this.getClass().getSimpleName(),
        //        this.operator == Operator.MUST_PASS_ALL ? "AND" : "OR",
                endIndex,
                this.filters.size(),
                this.filters.subList(0, endIndex).toString());
    }
}
