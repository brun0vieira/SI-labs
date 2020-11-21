:-ensure_loaded('RTXengine/RTXstrips_planner').

act goto_xz(Xf, Zf)
       pre [x_is_at(Xi), z_is_at(Zi), (Xi\==Xf, Zi\==Zf)]
       add [x_is_at(Xf), z_is_at(Zf)]
       del [x_is_at(Xi), z_is_at(Zi)]
       endcond [x_is_at(Xf), z_is_at(Zf)].


act goto_x(Xf)
       pre [x_is_at(Xi), (Xi\==Xf)]
       add [x_is_at(Xf)]
       del [x_is_at(Xi)]
       endcond [ x_is_at(Xf) ].

act goto_z(Zf)
       pre [z_is_at(Zi), (Zi\==Zf)]
       add [z_is_at(Zf)]
       del [z_is_at(Zi)]
       endcond [ z_is_at(Zf) ].

act put_in_cell(X,Z,Block)
       pre [cage(Block), x_is_at(X),z_is_at(Z), not(cell(X,Z,_))]
       add [cell(X,Z,Block)]
       del [cage(Block)]
       endcond [not(cage(Block)), cell(X,Z,Block)].

act take_from_cell(X,Z,Block)
       pre [not(cage(_)),x_is_at(X), z_is_at(Z), cell(X,Z,Block)]
       add [cage(Block)]
       del [cell(X,Z,Block)]
       endcond [cage(Block), not(cell(X,Z,Block))].

