:-ensure_loaded('RTXengine/RTXstrips_planner').
:-ensure_loaded('RTXengine/RTXutil').

act goto_xz(Xf, Zf)
       pre [x_is_at(Xi), z_is_at(Zi), (Xi\==Xf, Zi\==Zf)]
       add [x_is_at(Xf), z_is_at(Zf)]
       del [x_is_at(Xi), z_is_at(Zi)]
       endcond [x_is_at(Xf),z_is_at(Zf)].


act goto_x(Xf)
       pre [x_is_at(Xi), (Xi\==Xf)]
       add [x_is_at(Xf)]
       del [x_is_at(Xi)]
       endcond [x_is_at(Xf)].

act goto_z(Zf)
       pre [z_is_at(Zi), (Zi\==Zf)]
       add [z_is_at(Zf)]
       del [z_is_at(Zi)]
       endcond [z_is_at(Zf)].

act goto_y(Yf)
       pre [y_is_at(Yi), (Yi\==Yf)]
       add [y_is_at(Yf)]
       del [y_is_at(Yi)]
       endcond [y_is_at(Yf)].

act put_in_cell(X,Z,Block)
       pre [cage(Block), x_is_at(X),z_is_at(Z), not(cell(X,Z,_))]
       add [cell(X,Z,Block)]
       del [cage(Block)]
       endcond [not(cage_has_part),y_is_at(2),is_at_z_down].

act take_from_cell(X,Z,Block)
       pre [cell(X,Z,Block), x_is_at(X), z_is_at(Z), not(cage(_))]
       add [cage(Block)]
       del [cell(X,Z,Block)]
       %endcond [cage(Block), not(cell(X,Z,Block))].
       endcond [cage_has_part,y_is_at(2), is_at_z_down].

/*
act do_calibration_x
       pre []
       add[x_is_at(0)]
       del []
       endcond [x_is_at(0)].

act do_calibration_z
      pre []
      add[z_is_at(0)]
      del []
      endcond [z_is_at(0)].
*/

act pick_part_left_station(Block)
      pre [x_is_at(1), z_is_at(1), not(cage(Block)), not(cell(_,_,Block))]
      add [cage(Block)]
      del []
      endcond [cage_has_part, y_is_at(2), is_at_z_down].

act pick_part_right_station(Block)
     pre [x_is_at(10), z_is_at(1), not(cage(Block)),not(cell(_,_,Block))]
     add [cage(Block)]
     del []
     endcond [cage_has_part, y_is_at(2), is_at_z_down].


















