:-ensure_loaded('web_services').
:-ensure_loaded('warehouse_planner').

:- http_handler(root(execute_remote_query ), execute_remote_query    , []).
:- http_handler(root(query_dispatcher_json), query_dispatcher_json   , []).

:- http_handler(root(query_forward),	     query_forward	     , []).
:- http_handler(root(query_warehouse_states), query_warehouse_states, []).
:- http_handler(root(query_generate_plan), query_generate_plan, []).
:- http_handler(root(query_execute_plan), query_execute_plan, []).
:- http_handler(root(query_read_failures), query_read_failures  , []).
:- http_handler(root(query_recover_failures), query_recover_failures  , []).

:- http_handler(root(query_convert), query_convert, []).



:-include('dispatcher.pl').  % usando o "consult" apaga as regras dos outros m�dulos
:-include('monitoring.pl').
:-include('diagnosis.pl').
/*:-include('management_states.pl').*/
:-include('recovery.pl').

:-dynamic res_sult/1.
:-dynamic action/1.
:-dynamic failures_to_json/1.

%http://www.pathwayslms.com/swipltuts/html/index.html
%https://www.swi-prolog.org/pldoc/doc_for?object=section(%27packages/http.html%27)

:- dynamic yy/1.

%occupation or management states
:-dynamic cell/3, cage/1, left_station/1, right_station/1.

%sensors
:-dynamic x_is_at/1, y_is_at/1, z_is_at/1.
:-dynamic x_moving/1, y_moving/1, z_moving/1, left_station_moving/1, right_station_moving/1.
:-dynamic is_at_z_up/0, is_at_z_down/0, is_part_at_left_station/0, is_part_at_right_station/0, cage/0, cage_has_part/0.



main:- start_server(8083).
main(_):-
	start_server(8083).


start_server(Port):-
    server(Port),
    writeln('server started...').



:- json_object
    dispatch(action_name:atom) + [type=dispatch].


%NOVA VERSAO
execute_query([query=QueryString],Result):-
    term_string(QueryTerm, QueryString),
    catch(
     findall(true, QueryTerm, L),
	 error(Err,_Context)
	 ,
	 (   format('Erro: ~w\n', [Err]), L=[false])
    ),
    [Result|_]=L,
    !.
execute_query(_,false).

%NOVA VERSAO
%:-findall(Y, ( member(X,[1,2,3,4]), Y is X*2), L), write(L).

execute_remote_query(Request):-
	current_output(Curr),
        set_output(user_output),
        member(search(List), Request),
        set_output(Curr),
        format('Content-type: text/plain~n~n',[]),
        execute_query(List, Result),
        nl,
        writeln(Result).

query_dispatcher_json(_Request):-
	current_output(Curr),
        set_output(user_output),
         findall( dispatch(Action),
        (
           action(Action)
        ), ListOfActions),
        retractall(action(_)),
        set_output(Curr),
        format('Content-type: application/json~n~n', []),
        prolog_to_json(ListOfActions, JSON_EVENTS),
        json_write(current_output,JSON_EVENTS ).

query_forward(_Request):-
        current_output(Curr),
        set_output(user_output),
        forward,
        set_output(Curr),
        format('Content-type: text/plain~n~n',[]),
        nl,writeln('ok'),
        nl.

get_warehouse_states(ListOfStates):-
        findall( State,
		 (
		     %warehouse states
		     cell(X,Z,Part), State = cell(X,Z,Part);
		     left_station(Part), State = left_station(Part);
		     right_station(Part), State = right_station(Part);
		     cage(Part), State = cage(Part);

		     %sensor states
		     x_is_at(X), State = x_is_at(X);
		     y_is_at(Y), State = y_is_at(Y);
		     z_is_at(Z), State = z_is_at(Z);
		     x_moving(X), State = x_moving(X);
		     y_moving(Y), State = y_moving(Y);

		     % Complete with the other states
		     z_moving(Z), State = z_moving(Z);
		     left_station_moving(L), State = left_station_moving(L);
		     right_station_moving(R), State = right_station_moving(R);
		     is_at_z_up, State = is_at_z_up;
		     is_at_z_down, State = is_at_z_down;
		     is_part_at_left_station, State = is_part_at_left_station;
		     is_part_at_right_station, State = is_part_at_right_station;
		     cage_has_part, State = cage_has_part

		 ), ListOfStates).

query_warehouse_states(_Request):-
	current_output(Curr),
	set_output(user_output),
	get_warehouse_states(ListOfStates),
	set_output(Curr),
	format('Content-type: text/plain~n~n',[]),
	writeq(ListOfStates),nl.

query_generate_plan(Request):-
	current_output(Curr),
	set_output(user_output),
	member(search(List),Request),
	member(si=StatesIni, List), % initial state
	member(sf=StatesGoal, List), % final or goal state
	term_string(Si, StatesIni),
	term_string(Sf, StatesGoal),
	(
	    strips(Si, Sf, OriginatedPlan),!;
	    OriginatedPlan=[]
	),
	set_output(Curr),
	format('Content-type: text/plain~n~n',[]),
	writeq(OriginatedPlan).

query_convert(Request):-
	current_output(Curr),
	set_output(user_output),
	member(search(List),Request),
	member(sf=StatesGoal,List),
	term_string(Sf,StatesGoal),
	convert_to_blocks_world(Sf,Result),
	set_output(Curr),
	format('Content-type: text/plain~n~n',[]),
	writeq(Result).

query_execute_plan(Request):-
	current_output(Curr),
	set_output(user_output),
	member(search(List), Request),
	member(plan=PlanString, List),
	member(states=StatesString, List),

	term_string(Plan, PlanString),
	term_string(States, StatesString),

	prepare_the_plan(States, Plan, Sequence),
	launch_sequence(Sequence,ID),

	set_output(Curr),
	format('Content-type: text/plain~n~n',[]),
	writeq(sequence(ID)).

assert_goal(Goal):-
	assert(Goal),
	assert(goal(Goal)).

query_read_failures(_Request):-
	current_output(Curr),
	set_output(user_output),
	(
	    failures_to_json(Failure),
	    !;
	    Failure=no_failures
	),
	set_output(Curr),
        format('Content-type: text/plain~n~n',[]),
        writeq(Failure),
	!. % show one failure at the time

query_recover_failures(_Request):-
	current_output(Curr),
	set_output(user_output),
	recover_failure,
	set_output(Curr),
        format('Content-type: text/plain~n~n',[]),
        writeln(ok),
        !.   % recover a failure at a time

% Blocks world

convert_to_blocks_world(WarehouseStates, BlockWorldStates):-
    convert_to_blocks_world(WarehouseStates,WarehouseStates, BlockWorldStates).

convert_to_blocks_world(_,[],[]).

convert_to_blocks_world(InitialStates, [HouseFact|List],WorldStates):-
    convert_to_blocks_world(InitialStates, List, ResList_2),
    findall(WorldFact,   convert_block(HouseFact ,InitialStates, WorldFact),  NewFactsList),
    append(NewFactsList, ResList_2, WorldStates).

convert_block( cell(_X, 1, Block), _StatesList, ontable(Block)).

convert_block(    cage(Block), _StatesList, holding(Block)).

convert_block(cell(X, Z, Block), StatesList,    clear(Block)):-
    Zupper is Z+1,
    \+member(cell(X, Zupper, _), StatesList).

convert_block(cell(X, Z, Block_up), StatesList,    on(Block_up, Block_down)):-
    Zdown is Z-1,
    member(cell(X, Zdown, Block_down), StatesList).


