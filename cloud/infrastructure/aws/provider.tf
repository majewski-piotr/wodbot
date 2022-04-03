provider "aws" {
  region                  = var.AWS_REGION
  shared_credentials_file = "credentials"
  profile                 = "default"
}