package utilities.geometry.convexhull.algorithms

import utilities.geometry.Point
import utilities.geometry.PointsUtils.Points

case object MergeConvexHull extends ConvexHullAlgorithm with DivideAndConquer[List, Points, Point] {
  override protected def nonEmptyCalculate(points: Points): Points = execute(points)

  override protected def isTrivial[E](collection: List[E]): Boolean = collection.sizeIs == 1

  override protected def merge[E](collection: List[E]): E = ???

  override protected def map[A, B](cc: List[A], func: A => B): List[B] = cc.map(func)

  override protected def divide[E](t: List[E]): List[List[E]] = t.grouped(t.size / 2).toList

  override protected def finish(collection: List[Point]): Points = collection
}
