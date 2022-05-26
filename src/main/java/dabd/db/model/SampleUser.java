package dabd.db.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "SampleUser")
public class SampleUser implements Serializable
{
    private static final long serialVersionUID = 1L;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id  // isso indica que esse atributo é uma PK, ou parte dela
    @Column(name = "UserID")
    private int id;

    @Column(name = "UserName")
    private String name;

    @Column  // nem toda anotação Column precisa de um nome declarado
    private String phone;

    @Override
    public String toString() {
        return String.format(
            "ID: %d | Nome: %s | Telefone: %s",
            this.id, this.name, phone
            // o "this" (se refere ao objeto atual) é opcional, mas gosto de explicitar
        );
    }
}
