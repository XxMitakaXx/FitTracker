package org.example.fittracker.user.data

import jakarta.persistence.*
import org.example.fittracker.program.data.ProgramEntity
import java.util.*

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column
    val email: String,

    @Column
    val hashedPassword: String,

    @Column
    val username: String,

    @OneToMany(mappedBy = "user")
    val programs: MutableList<ProgramEntity> = mutableListOf(),
)