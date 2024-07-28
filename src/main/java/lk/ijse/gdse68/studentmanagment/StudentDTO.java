package lk.ijse.gdse68.studentmanagment;


import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class StudentDTO  implements Serializable {

    private String id;
    private String name;
    private String email;
    private String city;
    private String level;


}
