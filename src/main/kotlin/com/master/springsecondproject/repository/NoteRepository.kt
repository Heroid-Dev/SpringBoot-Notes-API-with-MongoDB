package com.master.springsecondproject.repository

import com.master.springsecondproject.model.Note
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface NoteRepository: MongoRepository<Note, ObjectId> {
    fun findByOwnerId(ownerId: ObjectId): List<Note>
    fun deleteByOwnerId(ownerId: ObjectId)
}