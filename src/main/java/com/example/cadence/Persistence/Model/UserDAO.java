package com.example.cadence.Persistence.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "User")
public class UserDAO
{
    @Id
    private String userId;
    private String contactNumber;
    private String emailId;
}
