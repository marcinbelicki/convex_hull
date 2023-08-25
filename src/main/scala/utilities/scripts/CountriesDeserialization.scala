package utilities.scripts

import utilities.serialization.JsonMapper

import java.io.File

object CountriesDeserialization extends App {

  private val deserializedCountries = JsonMapper.readValue(new File("data/countries.geojson"), classOf[Map[String, Any]])

  private val features = deserializedCountries("features").asInstanceOf[List[Map[String, Any]]]

  private val polandFeatures = features.find(_.get("properties").contains(Map("ADMIN" -> "Poland", "ISO_A3" -> "POL"))).get

  JsonMapper.writeValue(new File("data/poland.geojson"), polandFeatures)

}
