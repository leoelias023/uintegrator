# Database

For support all data store, mysql database ll'be used.

## Using mysql from docker

In this current directory, a Docker compose file `docker-compose.yml` is created for start a new instance for persistent data mysql.

In terminal, execute:

```
docker-compose up -d
```

The default credentials created for root user is (can be changed in `MYSQL_ROOT_PASSWORD` inside `docker-compose.yml`):

```
USER=root
PASSWORD=1234
PORT=3306
```

### Apply patches

Execute `*.sql` scripts insede `./patches`.