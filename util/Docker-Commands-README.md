docker-compose -f docker-compose-mysql.yml build                                
docker-compose -f docker-compose-mysql.yml --env-file docker-compose-vars.env up

