#### DB Initialization
1. First of all you need to create postgresql db named jobfinder. And make it available on port 5432
2. Then you have to run **mvn liquibase:update** 
3. To rollback changeSet you can use  **mvn liquibase:rollback -Dliquibase.rollbackCount=1**

