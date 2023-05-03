package hu.bme.aut.hiltbasicdemo

import javax.inject.Inject

class Info @Inject constructor(private val detail: Detail) {
    val text = "Hello Hilt ${detail.detailText}"
}

class Detail @Inject constructor() {
    val detailText = "Hello Detail"
}