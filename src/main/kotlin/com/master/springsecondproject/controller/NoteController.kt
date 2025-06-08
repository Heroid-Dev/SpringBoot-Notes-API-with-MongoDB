package com.master.springsecondproject.controller

import com.master.springsecondproject.model.Note
import com.master.springsecondproject.repository.NoteRepository
import org.bson.types.ObjectId
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping("/notes")
class NoteController(
    private val repository: NoteRepository
) {
    data class NoteRequest(
        val id: String?,
        val title: String,
        val content: String,
        val color: Long
    )

    data class NoteResponse(
        val id: String,
        val title: String,
        val content: String,
        val color: Long,
        val createAt: Instant
    )

    @PostMapping
    fun save(
        @RequestBody
        request: NoteRequest
    ): NoteResponse =
        repository.save(
            Note(
                id = request.id?.let { ObjectId(it) } ?: ObjectId(),
                title = request.title,
                content = request.content,
                color = request.color,
                ownerId = ObjectId(),
                createAt = Instant.now()
            )
        ).toResponse()

    @GetMapping("id")
    fun findByOwnerId(
        @RequestParam ownerId: String
    ): List<NoteResponse> =
        repository.findByOwnerId(ObjectId(ownerId)).map {
            it.toResponse()
        }

    @DeleteMapping("id")
    fun deleteByOwnerId(
        @RequestParam ownerId: String
    ) {
         repository.deleteByOwnerId(ownerId = ObjectId(ownerId))
    }

    @DeleteMapping(path = ["/{id}"])
    fun deleteById(@PathVariable id: String){
        repository.deleteById(ObjectId(id))
    }

    @GetMapping("all")
    fun getAll(): List<NoteResponse> =
        repository.findAll().map { it.toResponse() }

}

private fun Note.toResponse(): NoteController.NoteResponse =
    NoteController.NoteResponse(
        id = this.id.toHexString(),
        title = this.title,
        content = this.content,
        color = this.color,
        createAt = this.createAt
    )