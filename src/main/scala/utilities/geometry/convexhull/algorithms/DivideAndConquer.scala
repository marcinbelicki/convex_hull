package utilities.geometry.convexhull.algorithms

import scala.collection.IterableOps

trait DivideAndConquer[CC[_], T, U] {

  protected def isTrivial[E](collection: CC[E]): Boolean

  protected def divide[E](t: CC[E]): CC[CC[E]]

  protected def merge[E](collection: CC[E]): E

  protected def finish(collection: CC[U]): T

  protected def map[A, B](cc: CC[A], func: A => B): CC[B]

  final def execute(collection: CC[U]): T =
    if (isTrivial(collection)) finish(collection)
    else merge(map(divide(collection), execute))

}
