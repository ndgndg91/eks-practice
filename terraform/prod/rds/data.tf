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

data "aws_secretsmanager_secret_version" "auth" {
  secret_id = data.terraform_remote_state.secrets_manager.outputs.prod_auth_name
}