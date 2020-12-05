:-ensure_loaded('RTXengine/RTXengine.pl').
:-ensure_loaded('RTXengine/RTXutil.pl').

:-dynamic do_next/0.

defrule gotox_right
    if goto_x(Xf) and x_is_at(Xi) and (Xi<Xf) and x_moving(0)
    then [
        assert(action(move_x_right))
    ].

defrule gotox_left
    if goto_x(Xf) and x_is_at(Xi) and (Xi>Xf) and x_moving(0)
    then [
        assert(action(move_x_left))
    ].

defrule gotox_finish
    if goto_x(Xf) and x_is_at(Xf)
    then [
        assert(action(stop_x)),
        retract(goto_x(Xf))
    ].

defrule gotoy_inside
    if goto_y(Yf) and y_is_at(Yi) and (Yi<Yf) and y_moving(0)
    then [
        assert(action(move_y_inside))
    ].

defrule gotoy_outside
    if goto_y(Yf) and y_is_at(Yi) and (Yi>Yf) and y_moving(0)
    then [
        assert(action(move_y_outside))
    ].

defrule gotoy_finish
     if goto_y(Yf) and y_is_at(Yf)
     then [
         assert(action(stop_y)),
         retract(goto_y(Yf))
     ].

defrule gotoz_up
     if goto_z(Zf) and z_is_at(Zi) and (Zi<Zf) and z_moving(0)
     then [
         new_id(ID),
         Seq = [
             (   true, assert(action(move_z_up))),
             (   z_is_at(Zf), assert(action(stop_z)), retract_safe(goto_z(Zf)))
         ],
         assert(sequence(ID,gotoz_up_seq,Seq))
     ].

defrule gotoz_down
     if goto_z(Zf) and z_is_at(Zi) and (Zi>Zf) and z_moving(0)
     then [
         new_id(ID),
         Seq = [
             (   true, assert(action(move_z_down))),
             (   z_is_at(Zf), assert(action(stop_z)), retract_safe(goto_z(Zf)))
         ],
         assert(sequence(ID,gotoz_down_seq,Seq))
     ].

defrule gotoz_finish
     if goto_z(Zf) and z_is_at(Zf) and stop_z
     then [
         retract(goto_z(Zf))
     ].

defrule goto_xz
    if goto_xz(X,Z) and not(goto_x(_)) and not(goto_z(_))
    then [
        assert_once(goto_x(X)),
        assert_once(goto_z(Z)),
        retract(goto_xz(X,Z))
    ].

defrule give_part_right
     if give_part_right_station(Block) and cage_has_part
     then [
         new_id(ID),
         Seq = [
             (   true, assert(goto_xz(10,1))),
             (   (x_is_at(10),z_is_at(1)), assert(action(move_z_up))),
             (   is_at_z_up, assert(action(stop_z))),
             (   (is_at_z_up,z_moving(0)), assert(goto_y(1))),
             (   y_is_at(1), assert(action(move_z_down))),
             (   is_at_z_down, assert(action(stop_z))),
             (   (is_at_z_down,z_moving(0)), assert(goto_y(2)))
         ],
         assert(sequence(ID,give_part_right_seq,Seq)),
         retract_safe(give_part_right_station(Block))
     ].

defrule give_part_left
     if give_part_left_station(Block) and cage_has_part
     then [
         new_id(ID),
         Seq = [
             (   true, assert(goto_xz(1,1))),
             (   (x_is_at(1),z_is_at(1)), assert(action(move_z_up))),
             (   is_at_z_up, assert(action(stop_z))),
             (   (is_at_z_up,z_moving(0)), assert(goto_y(1))),
             (   y_is_at(1), assert(action(move_z_down))),
             (   is_at_z_down, assert(action(stop_z))),
             (   (is_at_z_down,z_moving(0)), assert(goto_y(2)))
         ],
         assert(sequence(ID,give_part_left_seq,Seq)),
         retract_safe(give_part_left_station(Block))
     ].

defrule pick_part_left
     if pick_closest_part(Block) and x_is_at(X) and (X=<5)
     then [
         new_id(ID),
         Seq = [
             (   true, assert(goto_xz(1,1))),
             (   (x_is_at(1),z_is_at(1)), assert(action(move_left_station_inside))),
             (   is_part_at_left_station, assert(action(stop_left_station))),
             (   true, assert(goto_y(1))),
             (   y_is_at(1), assert(action(move_z_up))),
             (   is_at_z_up, assert(action(stop_z))),
             (   (is_at_z_up,z_moving(0)), assert(goto_y(2))),
             (   y_is_at(2), assert(action(move_z_down))),
             (   is_at_z_down, assert(action(stop_z)))
         ],
         assert(sequence(ID,pick_part_left_seq,Seq)),
         retract_safe(pick_closest_part(Block))
     ].

defrule pick_part_right
     if pick_closest_part(Block) and x_is_at(X) and (X>5)
     then [
         new_id(ID),
         Seq = [
             (   true, assert(goto_xz(10,1))),
             (   (x_is_at(10),z_is_at(1)), assert(action(move_right_station_inside))),
             (   is_part_at_right_station, assert(action(stop_right_station))),
             (   true, assert(goto_y(1))),
             (   y_is_at(1), assert(action(move_z_up))),
             (   is_at_z_up, assert(action(stop_z))),
             (   (is_at_z_up,z_moving(0)), assert(goto_y(2))),
             (   y_is_at(2), assert(action(move_z_down))),
             (   is_at_z_down, assert(action(stop_z)))
         ],
         assert(sequence(ID,pick_part_right_seq,Seq)),
         retract_safe(pick_closest_part(Block))
     ].

defrule put_part_in_cell
     if put_in_cell(X,Z,Block) and cage_has_part
     then [
         new_id(ID),
         Seq = [
             (   true, assert(goto_xz(X,Z))),
             (   (x_is_at(X),z_is_at(Z)), assert(action(move_z_up))),
             (   is_at_z_up, assert(action(stop_z))),
             (   (is_at_z_up,z_moving(0)), assert(goto_y(3))),
             (   y_is_at(3), assert(action(move_z_down))),
             (   is_at_z_down, assert(action(stop_z))),
             (   (is_at_z_down,z_moving(0)), assert(goto_y(2)))
         ],
         assert(sequence(ID,put_part_in_cell_seq,Seq)),
         retract_safe(put_in_cell(X,Z,Block))
     ].


defrule take_part_from_cell
     if take_from_cell(X,Z,Block) %and not(cage_has_part)
     then [
         new_id(ID),
         Seq = [
             (   true, assert(goto_xz(X,Z))),
             (   (x_is_at(X),z_is_at(Z)), assert(goto_y(3))),
             (   y_is_at(3), assert(action(move_z_up))),
             (   is_at_z_up, assert(action(stop_z))),
             (   (is_at_z_up,z_moving(0)), assert(goto_y(2))),
             (   y_is_at(2), assert(action(move_z_down))),
             (   is_at_z_down, assert(action(stop_z)))
         ],
         assert(sequence(ID,take_part_from_cell_seq,Seq)),
         retract_safe(take_from_cell(X,Z,Block))
     ].

defrule actions_into_monitoring_goals
     if true
     then [
            findall(
               _IgnoredFact,
               (
                   action(Action),
                   retractall(goal(action(Action))),
                   assert(goal(action(Action)))
                ),
               _IgnoredList
            )
     ].

% Blocks world

defrule pickup
     if pickup(Block) and cell(X,Z,Block)
     then [
         retract(pickup(Block)),
         new_id(ID),
         Seq = [
             (   do_next, retractall(do_next)),
             (   true, assert(goto_xz(X,Z))),
             (   (x_is_at(X),z_is_at(Z)), assert(take_from_cell(X,Z,Block))),
             (   cage_has_part, assert(do_next))
         ],
         assert(sequence(ID,pickup_seq,Seq))
     ].

defrule putdown
     if putdown(Block) and not(cell(X,Z,_))
     then [
         retract(putdown(Block)),
         new_id(ID),
         Seq = [
             (   do_next, retractall(do_next)),
             (   true, assert(goto_xz(X,Z))),
             (   (x_is_at(X),z_is_at(Z)), put_in_cell(X,Z,Block)),
             (   not(cage_has_part), assert(do_next))
         ],
         assert(sequence(ID,putdown_seq,Seq))
     ].

defrule stack(A,B)
     if cell(B,X,Z) and holding(A)
     then [
         new_id(ID),
         Z2 is Z+1,
         Seq = [
             (   do_next, retractall(do_next)),
             (   true, assert(goto_xz(X,Z2))),
             (   (x_is_at(X),z_is_at(Z)), assert(put_in_cell(X,Z2,A))),
             (   not(cage_has_part), assert(do_next))

         ],
         assert(sequence(ID,stack_seq,Seq))
     ].

% falta testar unstack
defrule unstack(A,B)
    if cell(B,X,Z) and not(holding(_))
    then [
        new_id(ID),
        Z2 is Z-1,
        Seq = [
            (   do_next, retractall(do_next)),
            (   true, assert(goto_xz(X,Z2))),
            (   (x_is_at(X),z_is_at(Z)), assert(take_from_cell(X,Z2,A))),
            (   not(cage_has_part), assert(do_next))
        ],
        assert(sequence(ID,unstack_seq,Seq))

    ].

defrule save_states
    if true then [ save_management_states ].

