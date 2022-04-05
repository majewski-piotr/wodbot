provider "aws" {
  region                   = var.AWS_REGION
  shared_credentials_files = ["credentials"]
  profile                  = "default"
}