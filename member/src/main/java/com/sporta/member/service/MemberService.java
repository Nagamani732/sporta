package com.sporta.member.service;

import com.sporta.member.dto.MemberDTO;
import com.sporta.member.model.Member;
import com.sporta.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
    The service layer contains the business logic for handling Member objects.
    This class interacts with the MemberRepository and converts between entities and DTOs.
 */

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public MemberDTO createMember(MemberDTO memberDTO) {
        Member member = convertToEntity(memberDTO);
        // Set expiryDate based on membership type, e.g., one year from joinDate
        member.setMembershipExpiryDate(memberDTO.getJoinDate().plusMonths(1));
        member.setActive(true);
        Member savedMember = memberRepository.save(member);
        return convertToDTO(savedMember);
    }
    
    public Optional<MemberDTO> getMemberById(Long id) {
        return memberRepository.findById(id).map(this::convertToDTO);
    }
    
    public List<MemberDTO> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public MemberDTO updateMember(Long id, MemberDTO memberDTO) {
        return memberRepository.findById(id).map(existingMember -> {
            existingMember.setName(memberDTO.getName());
            existingMember.setEmail(memberDTO.getEmail());
            existingMember.setPhoneNumber(memberDTO.getPhoneNumber());
            existingMember.setJoinDate(memberDTO.getJoinDate());
            existingMember.setMembershipExpiryDate(memberDTO.getMembershipExpiryDate());
            existingMember.setActive(memberDTO.isActive());
            Member updatedMember = memberRepository.save(existingMember);
            return convertToDTO(updatedMember);
        }).orElseThrow(() -> new RuntimeException("Member not found"));
    }
    
    public void deleteMember(Long id) {
        if (memberRepository.existsById(id)) {
            memberRepository.deleteById(id);
        } else {
            throw new RuntimeException("Member not found");
        }
    }

    private MemberDTO convertToDTO(Member member) {
        MemberDTO dto = new MemberDTO();
        dto.setId(member.getId());
        dto.setName(member.getName());
        dto.setEmail(member.getEmail());
        dto.setPhoneNumber(member.getPhoneNumber());
        dto.setJoinDate(member.getJoinDate());
        dto.setMembershipExpiryDate(member.getMembershipExpiryDate());
        dto.setActive(member.isActive());
        return dto;
    }

    private Member convertToEntity(MemberDTO dto) {
        Member member = new Member();
        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
        member.setPhoneNumber(dto.getPhoneNumber());
        member.setJoinDate(dto.getJoinDate());
        return member;
    }
}
