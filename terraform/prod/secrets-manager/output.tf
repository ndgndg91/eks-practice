output "prod_seller_auth_name" {
    value = aws_secretsmanager_secret.prod_seller_auth.name
}

output "prod_buyer_auth_name" {
    value = aws_secretsmanager_secret.prod_buyer_auth.name
}