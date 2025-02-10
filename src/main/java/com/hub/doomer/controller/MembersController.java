package com.hub.doomer.controller;

import com.hub.doomer.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
@RequestMapping("search/members")
public class MembersController {

    private final MemberService memberService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String getMembersList(Model model) {
        model.addAttribute("members", this.memberService.findAllMembers());
        return "search/members/list";
    }
}
