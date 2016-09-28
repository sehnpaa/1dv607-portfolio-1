package com.boatclub.controller;

import com.boatclub.model.Boat;
import com.boatclub.model.BoatClub;
import com.boatclub.model.Member;
import com.boatclub.view.ConsoleUI;

public class Router {

    private ConsoleUI view;
    private BoatClub model;

    private UserAction[] availableActions = new UserAction[] {
        UserAction.AddMember,
        UserAction.ViewMember,
        UserAction.UpdateMember,
        UserAction.DeleteMember,
        UserAction.AddBoat,
        UserAction.Exit
    };

    public Router (BoatClub model, ConsoleUI view) {
        this.model = model;
        this.view = view;
    }

    public void start () throws Exception {
        boolean quit = false;

        view.displayWelcomeMessage();

        // Run until user wants to quit application
        while (!quit) {
            view.displayMenu(availableActions);
            UserAction choice = view.getUserRequest(availableActions);

            quit = tryHandleAction(choice);

            // Store member data after each action
            model.saveMemberData();
        }

        view.displayExitMessage();
    }

    private boolean tryHandleAction (UserAction choice) {
        try {
            return handleAction(choice);
        } catch (Exception error) {
            view.displayMemberNotFound();
            return false;
        }
    }

    private boolean handleAction (UserAction choice) throws Exception {
        boolean quitApplication = false;

        switch (choice) {
            case AddMember:
                showAddMember();
                break;
            case ViewMember:
                showMember();
                break;
            case UpdateMember:
                updateMember();
                break;
            case DeleteMember:
                showDeleteMember();
                break;
            case AddBoat:
                showAddBoat();
                break;
            case Exit:
                quitApplication = true;
                break;
            default:
                throw new Exception("This choice can not be performed");
        }

        return quitApplication;
    }

    private void showAddMember() throws Exception {
        String name = view.getInputMemberName();
        String pno = view.getInputMemberPno();

        model.addMember(name, pno);

        view.displayAddedMember();
    }

    private void showMember () throws Exception {
        Member member = getMember();

        view.displayMember(member.getName(), member.getPno(), member.getId());
    }

    private void updateMember () throws Exception {
        Member member = getMember();

        String newName = view.displayUpdateName(member.getName());
        String newPno = view.displayUpdatePno(member.getPno());

        member.setName(newName);
        member.setPno(newPno);
    }

    private void showDeleteMember () throws Exception {
        int id = view.getInputMemberId();

        model.deleteMember(id);

        view.displayMemberDeleted();
    }

    private void showAddBoat () throws Exception {
        Member member = getMember();
        float length = view.getInputBoatLength();

        member.addBoat(Boat.Type.Motorsailer, length);

        view.displayAddedBoat();
    }

    private Member getMember () throws Exception {
        int id = view.getInputMemberId();
        return model.getMember(id);
    }
}
