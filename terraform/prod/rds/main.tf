terraform {
  backend "s3" {
    region  = "ap-northeast-2"
    bucket  = "donggil-terraform-state"
    key     = "rds/terraform.tfstate"
    encrypt = true
  }
}

provider "aws" {
  region = var.region
}

resource "aws_db_parameter_group" "auth" {
  name   = "auth-pg"
  family = "mysql5.7"

  parameter {
    name  = "character_set_client"
    value = "utf8mb4"
  }

  parameter {
    name  = "character_set_connection"
    value = "utf8mb4"
  }

  parameter {
    name  = "character_set_database"
    value = "utf8mb4"
  }

  parameter {
    name  = "character_set_filesystem"
    value = "utf8mb4"
  }

  parameter {
    name  = "character_set_results"
    value = "utf8mb4"
  }

  parameter {
    name  = "character_set_server"
    value = "utf8mb4"
  }

  parameter {
    name  = "time_zone"
    value = "Asia/Seoul"
  }

  tags = {
    Owned = "donggil"
  }
}

resource "aws_db_subnet_group" "auth" {
  name = "seller-auth-mysql-5-7"
  subnet_ids = [
    data.terraform_remote_state.vpc.outputs.rds_private_1_subnet_id,
    data.terraform_remote_state.vpc.outputs.rds_private_2_subnet_id,
    data.terraform_remote_state.vpc.outputs.rds_private_3_subnet_id,
  ]
  description = "authentication service rds subnet group"

  tags = {
    Owned = "donggil"
  }
}

resource "aws_db_instance" "auth" {
  identifier           = "auth"
  allocated_storage    = 10
  engine               = "mysql"
  engine_version       = "5.7"
  instance_class       = "db.t3.micro"
  name                 = "auth"
  username             = jsondecode(data.aws_secretsmanager_secret_version.auth.secret_string).username
  password             = jsondecode(data.aws_secretsmanager_secret_version.auth.secret_string).password
  parameter_group_name = aws_db_parameter_group.auth.name
  db_subnet_group_name = aws_db_subnet_group.auth.name
  vpc_security_group_ids = [
    data.terraform_remote_state.security_group.outputs.rds_private_security_group_id,
  ]
  availability_zone   = data.terraform_remote_state.vpc.outputs.rds_private_1_subnet_availability_zone
  skip_final_snapshot = true

  tags = {
    Owned = "donggil"
  }
}