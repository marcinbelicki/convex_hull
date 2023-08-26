package utilities.geometry.convex.hull.algorithms

import org.scalatest.GivenWhenThen
import org.scalatest.wordspec.AnyWordSpecLike
import utilities.geometry.convexhull.algorithms.{Graham, Jarvis}
import utilities.geometry.ordering.OrientationOrdering.Indicator
import utilities.scripts.{PolandMapToTikz, PolishBorderData}

class AlgorithmComparisonTest extends AnyWordSpecLike with GivenWhenThen {
  private val grahamAlgorithm = Graham()
  private val jarvisAlgorithm = Jarvis()

  private val testData = PolishBorderData.polishBorderPoints

  "Algorithms" must {
    "give the same result for the same arguments" in {
      val resultOfGrahamAlgorithm = grahamAlgorithm.calculate(testData)
      val resultOfJarvisAlgorithm = jarvisAlgorithm.calculate(testData)

      assert(resultOfGrahamAlgorithm == resultOfJarvisAlgorithm)
    }
  }
}
