data "terraform_remote_state" "vpc" {
  backend = "s3"
  config = {
    bucket = var.remote_state_bucket
    key    = var.remote_vpc_key
    region = var.region
  }
}