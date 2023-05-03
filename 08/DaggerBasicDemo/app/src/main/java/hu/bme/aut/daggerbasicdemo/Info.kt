package hu.bme.aut.daggerbasicdemo

import javax.inject.Inject

class Info @Inject constructor(val detail: Detail) {
    val text = "Hello Dagger 2 ${detail.detailText}"
}

class Detail constructor(val nr: Int) {
    val detailText = "Hello Detail $nr"
}