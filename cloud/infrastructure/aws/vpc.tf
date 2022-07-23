resource "aws_vpc" "wodbot_vpc" {
  cidr_block           = "10.0.0.0/16"
  instance_tenancy     = "default"
  enable_dns_support   = "true"
  enable_dns_hostnames = "true"
  enable_classiclink   = "false"
  tags                 = {
    Name = "main"
  }
}

resource "aws_subnet" "wodbot_lambda_subnet" {
  vpc_id                  = aws_vpc.wodbot_vpc.id
  cidr_block              = "10.0.1.0/24"
  map_public_ip_on_launch = "false"
  availability_zone       = "eu-central-1a"

  tags = {
    Name = "main-private-1"
  }
}