package com.detectiveme.halper

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers.io

fun <T> Single<T>.backgroundWork(): Single<T> =
    this.subscribeOn(io())
        .observeOn(io())
