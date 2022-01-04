module "db" {
  source  = "terraform-aws-modules/rds/aws"
  version = "~> 3.0"

  identifier = "grupo4db"

  engine            = "mysql"
  engine_version    = "8.0.23"
  instance_class    = "db.t2.micro"
  allocated_storage = 5

  name     = "grupo4bd"
  username = "admin"
  password = "Integrador4"
  port     = "3306"

  vpc_security_group_ids = ["sg-06edda8aa93577ebc"]
  subnet_ids = ["subnet-0678bcf9b9ebf157a", "subnet-0f29babee4145bd43"]
  db_subnet_group_name = "default-vpc-0976a2736452b5249"
  major_engine_version = "8.0.23"
  iam_database_authentication_enabled = false
  parameter_group_use_name_prefix = false
  publicly_accessible = true
  create_db_subnet_group    = false
  create_db_parameter_group = false
  create_db_option_group    = false
  create_monitoring_role = false
  tags = {
    Owner       = "user"
    Environment = "dev"
  }

   # Database Deletion Protection
  deletion_protection = true

  

}