package com.utils

import kotlinx.browser.document
import org.w3c.dom.Document

fun Document.setPageMetadata(title: String, description: String) {
    this.title = title
    head!!
        .querySelector("meta[name='description']")!!
        .setAttribute("content", description)
}

fun Document.setDescription(description: String) {
    val head = document.head!!
    (head.querySelector("meta[name='description']") ?:
    document.createElement("meta").apply {
        setAttribute("name", "description")
        head.appendChild(this)
    }
            ).setAttribute("content", description)
}