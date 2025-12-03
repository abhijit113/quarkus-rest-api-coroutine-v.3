package com.stackdev.service

import com.stackdev.models.ClientDetails
import com.stackdev.repositories.ClientDetailsRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class ClientDetailsService(private val repository: ClientDetailsRepository) {
    @Transactional
    suspend fun create(client: ClientDetails): ClientDetails =
        run {
            repository.persist(client)
            client
        }

    suspend fun listAll(): List<ClientDetails> = repository.listAll()

    suspend fun findById(id: Long): ClientDetails? = repository.findById(id)

    @Transactional
    suspend fun update(
        id: Long,
        updated: ClientDetails,
    ): ClientDetails =
        run {
            val existing =
                repository.findById(id)
                    ?: throw jakarta.ws.rs.NotFoundException("Client with id $id not found")

            existing.apply {
                name = updated.name
                surname = updated.surname
                age = updated.age
                email = updated.email
                basicSalary = updated.basicSalary
            }
            existing
        }

    @Transactional
    suspend fun deleteById(id: Long): Boolean = repository.deleteById(id)
}
