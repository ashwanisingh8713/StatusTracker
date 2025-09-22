package com.indusjs.statustracker.pages.ujjwal

import androidx.compose.runtime.Composable
import com.stevdza.san.kotlinbs.models.SpinnerVariant
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.core.Page
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.H5
import org.jetbrains.compose.web.dom.Hr
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.TextArea
import org.jetbrains.compose.web.dom.Ul
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.css.px


@Page
@Composable
fun ContactPage() {
    @Composable
    fun BSLoader(variant: SpinnerVariant.LargeGrow) {

    }
    Div({ classes("container", "mt-14") }) {
        // Title
        H2(
            attrs = {
                classes("text-center", "mb-3")
                style {
                    fontSize(14.px)
                }
            }
        ) {
            Text("Contact Me")
        }
        var align = TextAlign.Center

        // Contact Form

                @Composable
                fun LoadingExample() {
                    // Ye Large Grow Spinner dikhayega
                    BSLoader(
                        variant = SpinnerVariant.LargeGrow
                    )
                }
            Div({ classes("row", "mb-3") }) {
                Div({ classes("col-md-6") }) {
                    Input(type = InputType.Text) {
                        classes("form-control")
                        attr("placeholder", "First Name")
                    }
                }
                Div({ classes("col-md-6") }) {
                    Input(type = InputType.Text) {
                        classes("form-control")
                        attr("placeholder", "Last Name")
                    }
                }
            }

            Div({ classes("mb-3") }) {
                Input(type = InputType.Text) {
                    classes("form-control")
                    attr("placeholder", "Subject")
                }
            }

            Div({ classes("mb-3") }) {
                TextArea {
                    classes("form-control")
                    attr("placeholder", "Message")
                    attr("rows", "4")
                }
            }

            // Gradient Button
            Button(attrs = {
                classes("btn", "w-100")
                style {
                    property("background", "linear-gradient(to right, #6a11cb, #2575fc)")
                    property("color", "white")
                    property("border-radius", "20px")
                    property("padding", "10px 20px")
                }
            }) {
                Text("Send Message ✉️")
            }
        }

        // Footer Section
        Hr()

        Div({ classes("row", "mt-4") }) {
            // My Projects
            Div({ classes("col-md-4", "mb-3") }) {
                H5 { Text("My Projects") }
                Ul {
                    Li {
                        A(href = "https://example.com", attrs = { attr("target", "_blank") }) {
                            Text("Nemo Handy Handheld Measurement Solution")
                        }
                    }
                    Li {
                        A(href = "https://example.com", attrs = { attr("target", "_blank") }) {
                            Text("The Hindu: India & World News")
                        }
                    }
                    Li {
                        A(href = "https://example.com", attrs = { attr("target", "_blank") }) {
                            Text("The Hindu BusinessLine")
                        }
                    }
                    Li { Text("Shorts News") }
                    Li { Text("KMP Shopify: Shopify Mobile Apps POC") }
                    Li { Text("KMP Project : THE HINDU") }
                }
            }

            // Quick Links
            Div({ classes("col-md-4", "mb-3") }) {
                H5 { Text("Quick Links") }
                Ul {
                    Li { A("#") { Text("Home") } }
                    Li { A("#") { Text("About Me") } }
                    Li { A("#") { Text("Skills") } }
                    Li { A("#") { Text("Experiences") } }
                    Li { A("#") { Text("Portfolio") } }
                    Li { A("#") { Text("Download CV") } }
                }
            }

            // Follow Me
            Div({ classes("col-md-4", "mb-3") }) {
                H5 { Text("Follow Me") }
                Ul {
                    Li {
                        A(href = "https://github.com", attrs = { attr("target", "_blank") }) {
                            Text("GitHub")
                        }
                    }
                    Li {
                        A(href = "https://linkedin.com", attrs = { attr("target", "_blank") }) {
                            Text("Linkedin")
                        }
                    }
                    Li {
                        A(href = "https://medium.com", attrs = { attr("target", "_blank") }) {
                            Text("Medium")
                        }
                    }
                }
            }
        }
    }

