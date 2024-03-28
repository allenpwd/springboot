// 创建超管用户
db.createUser(
   {
     user: "pwd",
     pwd: "123456",

     roles: [{"role":"root","db":"admin"}],
    /* All built-in Roles
     Database User Roles: read|readWrite
     Database Admin Roles: dbAdmin|dbOwner|userAdmin
     Cluster Admin Roles: clusterAdmin|clusterManager|clusterMonitor|hostManager
     Backup and Restoration Roles: backup|restore
     All-Database Roles: readAnyDatabase|readWriteAnyDatabase|userAdminAnyDatabase|dbAdminAnyDatabase
     Superuser Roles: root
    */

     // authenticationRestrictions: [ {
     //       clientSource: ["192.0.2.0"],
     //       serverAddress: ["198.51.100.0"]
     // } ],
     //mechanisms: [ "SCRAM-SHA-1","SCRAM-SHA-256"],
     //passwordDigestor: "server|client",
   }
)