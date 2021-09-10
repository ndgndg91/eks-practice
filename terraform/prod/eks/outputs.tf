output "eks_cluster_id" {
  value = aws_eks_cluster.eks_workshop_cluster.id
}

output "eks_cluster_name" {
  value = aws_eks_cluster.eks_workshop_cluster.name
}

output "eks_cluster_certificate_data" {
  value = aws_eks_cluster.eks_workshop_cluster.certificate_authority.0.data
}

output "eks_cluster_endpoint" {
  value = aws_eks_cluster.eks_workshop_cluster.endpoint
}

output "eks_cluster_nodegroup_id" {
  value = aws_eks_node_group.eks_workshop_node_group.id
}