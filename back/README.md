Pour lancer, il suffit d'installer wildfly en téléchargeant l'[archive sur le site officiel](https://github.com/wildfly/wildfly/releases/download/31.0.1.Final/wildfly-31.0.1.Final.zip) et lancer le serveur web avec `./bin/standalone.sh`, ca ouvre sur le port 8080.

Pour déployer le TP sur wildfly, il suffit d'installer maven et de faire `mvn wildfly:deploy` dans un autre terminal dans le répertoire du TP, ca va construire le `.war` et le mettre dans le dossier de wildfly.

Persistence DB : `<property name="hibernate.hbm2ddl.auto" value="update" />`


Pour ignore le checkstyle : `-Dcheckstyle.skip=true`