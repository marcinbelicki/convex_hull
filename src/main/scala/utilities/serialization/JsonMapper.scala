package utilities.serialization

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

object JsonMapper extends ObjectMapper {
  registerModules(DefaultScalaModule)
}
