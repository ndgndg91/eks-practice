data "terraform_remote_state" "vpc" {
  backend = "s3"
  config = {
    bucket = var.remote_state_bucket
    key    = var.remote_vpc_state_key
    region = var.region
  }
}

data "terraform_remote_state" "security_group" {
  backend = "s3"
  config = {
    bucket = var.remote_state_bucket
    key    = var.remote_security_group_key
    region = var.region
  }
}

data "terraform_remote_state" "secrets_manager" {
  backend = "s3"
  config = {
    bucket = var.remote_state_bucket
    key    = var.remote_secrets_manager_key
    region = var.region
  }
}

data "aws_secretsmanager_secret_version" "seller_auth" {
  secret_id = data.terraform_remote_state.secrets_manager.outputs.prod_seller_auth_name
}

data "aws_secretsmanager_secret_version" "reetest_admin_rds" {
  secret_id = data.terraform_remote_state.secrets_manager.outputs.prod_buyer_auth_name
}