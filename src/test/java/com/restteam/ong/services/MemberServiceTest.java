package com.restteam.ong.services;

import com.restteam.ong.models.Member;
import com.restteam.ong.repositories.MemberRepository;
import com.restteam.ong.services.impl.MemberServiceImpl;
import com.restteam.ong.util.PageableCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;

public class MemberServiceTest {

    MemberRepository memberRepositoryMock = Mockito.mock(MemberRepository.class);

    PageableCreator pageableCreator;

    MemberService service = new MemberServiceImpl(memberRepositoryMock,pageableCreator);

    Member memberMock3WithOutId;

    Member memberMock1;

    Member memberMock2;

    Member newMemberMock3;

    Member memberMock4WithOutName;

    @BeforeEach
    public void setUp(){
        memberMock1 = new Member();
        memberMock1.setId(1L);
        memberMock1.setName("member1");
        memberMock1.setFacebookUrl("htt´://facebook.member1");
        memberMock1.setInstagramUrl("htt´://instagram.member1");
        memberMock1.setLinkedinUrl("htt´://linkedin.member1");
        memberMock1.setImage("imagenMember1");
        memberMock1.setDescription("descripcionMember1");
        memberMock1.setDeleted(false);
        memberMock1.setCreatedAt(null);
        memberMock1.setUpdatedAt(20210709L);

        memberMock2 = new Member();
        memberMock2.setId(2L);
        memberMock2.setName("member2");
        memberMock2.setFacebookUrl("htt´://facebook.member2");
        memberMock2.setInstagramUrl("htt´://instagram.member2");
        memberMock2.setLinkedinUrl("htt´://linkedin.member2");
        memberMock2.setImage("imagenMember2");
        memberMock2.setDescription("descripcionMember2");
        memberMock2.setDeleted(false);
        memberMock2.setCreatedAt(20210709L);
        memberMock2.setUpdatedAt(20210709L);

        memberMock3WithOutId = new Member();
        memberMock3WithOutId.setName("member3");
        memberMock3WithOutId.setFacebookUrl("htt´://facebook.member3");
        memberMock3WithOutId.setInstagramUrl("htt´://instagram.member3");
        memberMock3WithOutId.setLinkedinUrl("htt´://linkedin.member3");
        memberMock3WithOutId.setImage("imagenMember3");
        memberMock3WithOutId.setDescription("descripcionMember3");
        memberMock3WithOutId.setDeleted(false);
        memberMock3WithOutId.setCreatedAt(20210709L);
        memberMock3WithOutId.setUpdatedAt(20210709L);

        newMemberMock3 = new Member();
        newMemberMock3.setId(3L);
        newMemberMock3.setName("member3");
        newMemberMock3.setFacebookUrl("htt´://facebook.member3");
        newMemberMock3.setInstagramUrl("htt´://instagram.member3");
        newMemberMock3.setLinkedinUrl("htt´://linkedin.member3");
        newMemberMock3.setImage("imagenMember3");
        newMemberMock3.setDescription("descripcionMember3");
        newMemberMock3.setDeleted(false);
        newMemberMock3.setCreatedAt(20210709L);
        newMemberMock3.setUpdatedAt(20210709L);

        memberMock4WithOutName = new Member();
        memberMock4WithOutName.setId(4L);
        memberMock4WithOutName.setName("");
        memberMock4WithOutName.setFacebookUrl("htt´://facebook.member4");
        memberMock4WithOutName.setInstagramUrl("htt´://instagram.member4");
        memberMock4WithOutName.setLinkedinUrl("htt´://linkedin.member4");
        memberMock4WithOutName.setImage("imagenMember4");
        memberMock4WithOutName.setDescription("descripcionMember4");
        memberMock4WithOutName.setCreatedAt(20210709L);
        memberMock4WithOutName.setUpdatedAt(20210709L);




        //Lista de 2 member
        List<Member> memberList =new ArrayList<>();
        memberList.add(memberMock1);
        memberList.add(memberMock2);

        //findAll
        Mockito.when(memberRepositoryMock.findAll()).thenReturn(memberList);
        //save
       // Mockito.when(memberRepositoryMock.save(memberMock3)).thenReturn(newMemberMock3);
        Mockito.when(memberRepositoryMock.save(newMemberMock3)).thenReturn(newMemberMock3);
        //getId
        Mockito.when(memberRepositoryMock.findById(memberMock1.getId())).thenReturn(Optional.of(memberMock1));
        Mockito.when(memberRepositoryMock.findById(memberMock3WithOutId.getId())).thenReturn(Optional.of(memberMock3WithOutId));
        //exist
        Mockito.when(memberRepositoryMock.existsById(memberMock3WithOutId.getId())).thenReturn(false);
        Mockito.when(memberRepositoryMock.existsById(memberMock1.getId())).thenReturn(true);

    }

    //addMember
    // dado un member sin nombre, devuelva un optional empty
    @Test
    void  addMember_without_name_return_optional_empty_test(){
        Assertions.assertEquals(service.addMember(memberMock4WithOutName), Optional.empty());
    }

    //dado un member con nombre , retorne un id y setCreateAll no sea null
    @Test
    void  addMember_with_name_return_same_member_test(){
       // no puede ser testeado por que el id es asignado por hibernate
        // Assertions.assertEquals(service.addMember(memberMock3).get(),newMemberMock3.getId());
        service.addMember(newMemberMock3);
        verify(memberRepositoryMock).save(newMemberMock3);
    }

    //getMembers
    // getMembers debe devolver un lista los members de la base
    //Test desactualizado, ahora getMembers utiliza paginacion.
    /*@Test
    void getMembers_return_two_element(){
        Assertions.assertEquals(service.getMembers(pageableCreator.goToPage(pageId)).size() , 2);
        Assertions.assertEquals(service.getMembers(pageableCreator.goToPage(pageId)).get(0).getName(), memberMock1.getName());
        Assertions.assertEquals(service.getMembers(pageableCreator.goToPage(pageId)).get(1).getName(),memberMock2.getName());
    }*/

    //upDateMember
    // cuando no existe id devuelve un optional empty
    @Test
    void updateMember_without_id_return_empty(){
        Assertions.assertEquals(service.updateMember(memberMock4WithOutName.getId(), memberMock4WithOutName.getName(), memberMock4WithOutName.getFacebookUrl(), memberMock4WithOutName.getInstagramUrl(), memberMock4WithOutName.getLinkedinUrl(),
                memberMock4WithOutName.getImage(), memberMock4WithOutName.getDescription()), Optional.empty());
    }
    // test de modificacion member y lo retorna
    @Test
    void updateMember_return_optional_member(){
        Assertions.assertEquals(service.updateMember(memberMock1.getId(), memberMock1.getName(), memberMock1.getFacebookUrl(), memberMock1.getInstagramUrl(), memberMock1.getLinkedinUrl(),
                memberMock1.getImage(), memberMock1.getDescription()) , Optional.of(memberMock1));
            }

    //deleteMember
    //cuando no existe id devuelve un optional empty

    @Test
    void deleteMember_without_id_return_empty() {
        Assertions.assertEquals(service.deleteMember(memberMock3WithOutId.getId()), Optional.empty());
    }
    //testear que devuelva el  member
    @Test
    void deleteMember_return_id(){
        Assertions.assertEquals(service.deleteMember(memberMock1.getId()),Optional.of(memberMock1.getId()));
        verify(memberRepositoryMock).deleteById(memberMock1.getId());
    }
}
