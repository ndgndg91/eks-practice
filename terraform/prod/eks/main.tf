terraform {
  backend "s3" {
    region  = "ap-northeast-2"
    bucket  = "donggil-terraform-state"
    key     = "eks/terraform.tfstate"
    encrypt = true
  }
}

provider "aws" {
  region = var.region
}

# EKS Cluster Resources
#  * IAM Role to allow EKS service to manage other AWS services
#  * EC2 Security Group to allow networking traffic with EKS cluster
#  * EKS Cluster
#

resource "aws_iam_role" "eks_workshop_cluster" {
  name = "eks-workshop-cluster"

  assume_role_policy = <<POLICY
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": {
        "Service": "eks.amazonaws.com"
      },
      "Action": "sts:AssumeRole"
    }
  ]
}
POLICY

  tags = {
    Owned = "donggil"
  }
}

resource "aws_iam_role_policy_attachment" "eks_workshop_cluster_AmazonEKSClusterPolicy" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSClusterPolicy"
  role       = aws_iam_role.eks_workshop_cluster.name
}

resource "aws_eks_cluster" "eks_workshop_cluster" {
  name     = "eks-workshop-cluster"
  role_arn = aws_iam_role.eks_workshop_cluster.arn

  vpc_config {
    subnet_ids = [
      data.terraform_remote_state.vpc.outputs.application_public_1_subnet_id,
      data.terraform_remote_state.vpc.outputs.application_public_2_subnet_id,
      data.terraform_remote_state.vpc.outputs.application_public_3_subnet_id,
      data.terraform_remote_state.vpc.outputs.application_private_1_subnet_id,
      data.terraform_remote_state.vpc.outputs.application_private_2_subnet_id,
      data.terraform_remote_state.vpc.outputs.application_private_3_subnet_id,
    ]
  }

  depends_on = [
    aws_iam_role_policy_attachment.eks_workshop_cluster_AmazonEKSClusterPolicy
  ]

  tags = {
    Owned = "donggil"
  }
}


#
# EKS Worker Nodes Resources
#  * IAM role allowing Kubernetes actions to access other AWS services
#  * EKS Node Group to launch worker nodes
#

resource "aws_iam_role" "eks_workshop_node" {
  name = "${aws_eks_cluster.eks_workshop_cluster.name}.node"

  assume_role_policy = <<POLICY
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
POLICY
  tags = {
    Owned = "donggil"
  }
}

data "http" "eks_workshop_node_IngressController" {
  url = "https://raw.githubusercontent.com/kubernetes-sigs/aws-alb-ingress-controller/master/docs/examples/iam-policy.json"

  request_headers = {
    Accept = "application/json"
  }
}

resource "aws_iam_role_policy" "eks_workshop_node_IngressControllerPolicy" {
  name   = "IngressControllerPolicy"
  role   = aws_iam_role.eks_workshop_node.name
  policy = data.http.eks_workshop_node_IngressController.body
}

resource "aws_iam_role_policy_attachment" "eks_workshop_node_AmazonEKSWorkerNodePolicy" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSWorkerNodePolicy"
  role       = aws_iam_role.eks_workshop_node.name
}

resource "aws_iam_role_policy_attachment" "eks_workshop_node_AmazonEKS_CNI_Policy" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKS_CNI_Policy"
  role       = aws_iam_role.eks_workshop_node.name
}

resource "aws_iam_role_policy_attachment" "eks_workshop_node_AmazonEC2ContainerRegistryReadOnly" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEC2ContainerRegistryReadOnly"
  role       = aws_iam_role.eks_workshop_node.name
}

resource "aws_eks_node_group" "eks_workshop_node_group" {
  cluster_name    = aws_eks_cluster.eks_workshop_cluster.name
  node_group_name = "eks-workshop-node-group"
  node_role_arn   = aws_iam_role.eks_workshop_node.arn
  subnet_ids = [
    data.terraform_remote_state.vpc.outputs.application_private_1_subnet_id,
    data.terraform_remote_state.vpc.outputs.application_private_2_subnet_id,
    data.terraform_remote_state.vpc.outputs.application_private_3_subnet_id,
  ]

  scaling_config {
    desired_size = 2
    max_size     = 5
    min_size     = 1
  }

  instance_types = ["t3.medium"]

  depends_on = [
    aws_iam_role_policy_attachment.eks_workshop_node_AmazonEKSWorkerNodePolicy,
    aws_iam_role_policy_attachment.eks_workshop_node_AmazonEKS_CNI_Policy,
    aws_iam_role_policy_attachment.eks_workshop_node_AmazonEC2ContainerRegistryReadOnly,
  ]

  tags = {
    Owned                                                                    = "donggil"
    "k8s.io/cluster-autoscaler/${aws_eks_cluster.eks_workshop_cluster.name}" = "owned"
    "k8s.io/cluster-autoscaler/enabled"                                      = "TRUE"
  }
}