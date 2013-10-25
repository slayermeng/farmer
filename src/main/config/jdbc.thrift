namespace java org.farmer.jdbc
 service Jdbc{
  string execute(1:string sql)
  list fetchN(1:int fetchSize)
 }