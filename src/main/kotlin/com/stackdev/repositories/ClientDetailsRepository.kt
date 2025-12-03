package com.stackdev.repositories

import com.stackdev.models.ClientDetails
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class ClientDetailsRepository : PanacheRepository<ClientDetails>
