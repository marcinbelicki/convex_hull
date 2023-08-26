package utilities.geometry.convexhull.algorithms

import scala.collection.IterableOps

trait DivideAndConquer[CC[_], T] {

  protected def isTrivial[E](collection: CC[E]): Boolean

  protected def divide[E](t: CC[E]): CC[CC[E]]

  protected def merge[E](collection: CC[E]): E

  protected def finish[E](collection: CC[E]): T

  protected def map[A, B](cc: CC[A], func: A => B): CC[B]

  final def execute[E](collection: CC[E]): T =
    if (isTrivial(collection)) finish(collection)
    else merge(map(divide(collection), execute[E]))

}
