package au.com.simplemachines.scala.credstash

import au.com.simplemachines.scala.credstash.reader.{ CredValueReader, Readers }

object BaseClient {

  type EncryptionContext = Map[String, String]
  val EmptyEncryptionContext: EncryptionContext = Map()

  val DefaultCredentialTableName = "credential-store"
  val DefaultCharacterEncoding = "UTF-8"
}

trait BaseClient {

  import BaseClient._

  def as[K](name: String, table: String = DefaultCredentialTableName, version: String = "-1", context: EncryptionContext = EmptyEncryptionContext)(implicit reader: CredValueReader[K]): Option[K]

  def get(name: String, table: String = DefaultCredentialTableName, version: String = "-1", context: EncryptionContext = EmptyEncryptionContext) = {
    as[String](name, table, version, context)(Readers.asString)
  }
}

trait AmazonClients {
  type KmsClient
  type DynamoClient

  val kmsClient: KmsClient
  val dynamoClient: DynamoClient
}

trait EncryptionClients {
  val aesEncryption: AESEncryption
}
