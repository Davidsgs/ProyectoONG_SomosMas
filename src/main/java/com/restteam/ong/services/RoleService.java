package com.restteam.ong.services;

import com.restteam.ong.models.Role;
import com.restteam.ong.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    // --- Método de Creación (Create) ---

    public Role createRole(Role role) {
        //Si el rol no es Nulo y el ID es nulo (ya que un rol "no registrado" no tiene ID).
        if (role != null && role.getId() == null) {
            //Se le agrega el timeStamp de creacion.
            role.setCreatedAt(System.currentTimeMillis() / 1000);
            return roleRepository.save(role);
        } else if (role == null) {
            //Error: El Rol no debe ser nulo.
            throw new IllegalStateException("The Role can't be null");
        } else {
            //Error: No se puede registrar un Rol que tenga ID. (ya que si es un nuevo rol no debe tenerlo.)
            throw new IllegalStateException("Can't register a new Role that has ID.");
        }
    }


    // --- Métodos de Lectura (Read) ---

    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(
                () -> new IllegalStateException(String.format("The role with id %d not found.", id))
        );
    }

    public Role findByName(String name){
        return roleRepository.findByName(name).orElseThrow(
                () -> new IllegalStateException(String.format("The role with name %s not found.", name))
        );
    }

    // --- Método de Actualizar (Update) ---

    @Transactional
    public Role updateRole(Long id, Role role){
        var roleToUpdate = findById(id);
        if(!role.getName().isBlank()){ roleToUpdate.setName(role.getName());}
        if(!role.getDescription().isBlank()){ roleToUpdate.setDescription(role.getDescription());}
        roleToUpdate.setUpdatedAt(System.currentTimeMillis() / 1000);
        return roleToUpdate;
    }

    // --- Método de Borrado (Delete) ---

    public Boolean deleteRole(Long id) {
        try {
            var roleToDelete = findById(id);
            roleRepository.delete(roleToDelete);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
