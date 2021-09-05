terraform {
  backend "s3" {
    region  = "ap-northeast-2"
    bucket  = "eks-terraform-workshop-ndgndg91"
    key     = "bastion/terraform.tfstate"
    encrypt = true
  }
}

provider "aws" {
  region = var.region
}

resource "aws_iam_role" "ssm" {
    name = "ssm-ec2"
    assume_role_policy = <<EOF
{
        "Version" : "2012-10-17",
        "Statement" : [
            {
                "Action" :"sts:AssumeRole",
                "Principal" : {
                    "Service" : "ec2.amazonaws.com"
                },
                "Effect" : "Allow",
                "Sid" : "EksPracticeSSMRole"
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
    name = "ssm-core"
    roles = [
        aws_iam_role.ssm.id,
    ]
    policy_arn = "arn:aws:iam::aws:policy/AmazonSSMManagedInstanceCore"
}

resource "aws_iam_policy_attachment" "ssm_ec2" {
    name = "ssm-ec2"
    roles = [
        aws_iam_role.ssm.id,
    ]
    policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonEC2RoleforSSM"
}

resource "aws_instance" "bastion" {
    ami = "ami-08c64544f5cfcddd0"
    instance_type = "t2.micro"
    vpc_security_group_ids = [
        data.terraform_remote_state.security_group.outputs.bastion_host_security_group_id,
    ]
    subnet_id = data.terraform_remote_state.vpc.outputs.application_public_1_subnet_id
    key_name = "eks-terraform"
    iam_instance_profile = aws_iam_instance_profile.ssm.id
    user_data = file("./install-ssm.sh")
}