:-ensure_loaded('RTXengine/RTXengine.pl').
:-ensure_loaded('RTXengine/RTXutil.pl').

defrule gotox_right
   if goto_x(Xf)  and x_is_at(Xi) and (Xi<Xf) and x_moving(0)
   then [
       assert( action(move_x_right))
   ].

defrule gotox_left
   if goto_x(Xf)  and x_is_at(Xi) and (Xi>Xf) and x_moving(0)
   then [
       assert( action(move_x_left))
   ].

defrule gotox_finish
   if goto_x(Xf) and x_is_at(Xf)
   then [
       assert(action(stop_x)),
       retract_safe(goto_x(Xf))
   ].

defrule gotoz_up
     if goto_z(Zf) and z_is_at(Zi) and (Zi<Zf) and z_moving(0)
     then [
         new_id(ID),
         Seq = [
                 ( true,          assert(action(move_z_up))),
                 ( z_is_at(Zf),   assert(action(stop_z)))
               ],
         assert(sequence(ID, gotoz_up_seq,Seq)),
         retract(goto_z(Zf))
     ].


defrule gotoz_down
     if goto_z(Zf) and z_is_at(Zi) and (Zi>Zf) and z_moving(0)
     then [
         new_id(ID),
         Seq = [
                 ( true,         assert(action(move_z_down))),
                 ( z_is_at(Zf),  assert(action(stop_z)))
         ],
         assert(sequence(ID, gotoz_down_seq, Seq)),
         retract(goto_z(Zf))
     ].

defrule goto_xz
    if  goto_xz(X,Z) and not(goto_x(_)) and not(goto_z(_)) %deixa fazer goto_xz(X,Z) simultaneaos
    then [
       assert_once(goto_x(X)),
       assert_once(goto_z(Z)),
       retract(goto_xz(X,Z))
    ].

defrule gotoy_inside
   if goto_y(Yf)  and y_is_at(Yi) and (Yi<Yf) and y_moving(0)
   then [
       assert(action(move_y_inside))
   ].


defrule gotoy_outside
   if goto_y(Yf)  and y_is_at(Yi) and (Yi>Yf) and y_moving(0)
   then [
       assert(action(move_y_outside))
   ].


defrule gotoy_finish
   if goto_y(Yf) and y_is_at(Yf)
   then [
       assert(action(stop_y)),
       retract_safe(goto_y(Yf))
   ].

defrule store_pallete
       if put_in_cell(X,Z,Block) and cage_has_part
       then [
           new_id(ID),
           Seq = [
               (   true, assert(goto_xz(X,Z))),
               (   (x_is_at(X), z_is_at(Z), z_moving(0), x_moving(0)), assert(action(move_z_up))),
               (   is_at_z_up, assert(action(stop_z))),
               (   (is_at_z_up,z_moving(0)), assert(goto_y(3))),
               (   y_is_at(3), assert(action(move_z_down))),
               (   is_at_z_down, assert(action(stop_z))),
               (   (is_at_z_down, z_moving(0)), assert(goto_y(2)))
           ],
           assert(sequence(ID,store_pallet_seq,Seq)),
           retract(put_in_cell(X,Z,Block))
       ].

defrule pick_pallete
       if take_from_cell(X,Z,Block)
       then [
           new_id(ID),
           Seq = [
               (   true, assert(goto_xz(X,Z))),
               (   (x_is_at(X), z_is_at(Z), x_moving(0), z_moving(0)), assert(action(move_z_down))),
               (   is_at_z_down, assert(action(stop_z))),
               (   (is_at_z_down, z_moving(0)), assert(goto_y(3))),
               (   y_is_at(3), assert(action(move_z_up))),
               (   is_at_z_up, assert(action(stop_z))),
               (   (is_at_z_up, z_moving(0)), assert(goto_y(2)))
          ],
          assert(sequence(ID,pick_pallete_seq(Seq))),
          retract(take_from_cell(X,Z,Block))
       ].

defrule pick_part_left
       if pick_part_left_station(Block)
       then [
           new_id(ID),
           Seq = [
               (   true, assert(goto_xz(1,1))),
               (   (x_is_at(1),z_is_at(1)), assert(action(move_left_station_inside))),
               (   is_part_at_left_station, assert(action(stop_left_station))),
               (   true, assert(goto_y(1))),
               (   y_is_at(1), assert(action(move_z_up))),
               (   is_at_z_up, assert(action(stop_z))),
               (   (is_at_z_up, z_moving(0)), assert(goto_y(2))),
               (   y_is_at(2), assert(action(move_z_down))),
               (   is_at_z_down, assert(action(stop_z)))
           ],
           assert(sequence(ID, pick_part_left_seq, Seq)),
           retract_safe(pick_part_left_station(Block))
       ].

defrule pick_part_right
       if pick_part_right_station(Block)
       then [
           new_id(ID),
           Seq = [
               (   true, assert(goto_xz(10,1))),
               (   (x_is_at(10),z_is_at(1)), assert(action(move_right_station_inside))),
               (   is_part_at_right_station, assert(action(stop_right_station))),
               (   true, assert(goto_y(1))),
               (   y_is_at(1), assert(action(move_z_up))),
               (   is_at_z_up, assert(action(stop_z))),
               (   (is_at_z_up, z_moving(0)), assert(goto_y(2))),
               (   y_is_at(2), assert(action(move_z_down))),
               (   is_at_z_down, assert(action(stop_z)))
           ],
           assert(sequence(ID, pick_part_right_seq, Seq)),
           retract_safe(pick_part_right_station(Block))
       ].

