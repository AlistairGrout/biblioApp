package com.example.iclu.bilbioapp.api

data class VolumeInfo(
        var title: String,
        var authors: List<String>,
        var publisher: String,
        var publishedDate: String,
        var description: String,
        var industryIdentifiers: List<GoogleBooksIdentifier>,
        var pageCount: Int,
        var categories: String,
        var imageLinks: GoogleBooksImage
)