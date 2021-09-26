terraform {
  backend "s3" {
    region  = "ap-northeast-2"
    bucket  = "donggil-terraform-state"
    key     = "bastion/terraform.tfstate"
    encrypt = true
  }
}

provider "aws" {
  region = var.region
}

resource "aws_iam_role" "ssm" {
  name               = "SSMInstanceRole"
  assume_role_policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": {
        "Service": "ec2.amazonaws.com"
      },
      "Action": "sts:AssumeRole"
    }
  ]
}
    EOF
}

resource "aws_iam_instance_profile" "ssm" {
  name = "ssm-ec2"
  role = aws_iam_role.ssm.id
}

resource "aws_iam_policy_attachment" "ssm_core" {
  name = "SSMInstanceProfile"
  roles = [
    aws_iam_role.ssm.id,
  ]
  policy_arn = "arn:aws:iam::aws:policy/AmazonSSMManagedInstanceCore"
}

resource "aws_iam_policy_attachment" "ssm_directory" {
  name = "ssm-directory"
  roles = [
    aws_iam_role.ssm.id,
  ]
  policy_arn = "arn:aws:iam::aws:policy/AmazonSSMDirectoryServiceAccess"
}

resource "aws_instance" "bastion" {
  depends_on = [
    aws_iam_role.ssm,
    aws_iam_instance_profile.ssm,
    aws_iam_policy_attachment.ssm_core
  ]

  ami           = "ami-08c64544f5cfcddd0"
  instance_type = "t2.micro"
  vpc_security_group_ids = [
    data.terraform_remote_state.security_group.outputs.application_private_subnet_security_group_id,
  ]
  subnet_id            = data.terraform_remote_state.vpc.outputs.application_private_1_subnet_id
  key_name             = "eks-terraform"
  iam_instance_profile = aws_iam_instance_profile.ssm.id
  user_data            = file("./install-ssm.sh")

  tags = {
    Owned = "donggil"
  }
}