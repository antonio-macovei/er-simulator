Antonio Dan Macovei, 324CA

Data începerii temei: 28.12.2018
Data finalizării temei: 05.01.2019

ER Simulator

Simularea incepe prin citirea datelor de intrare in format JSON si parsarea
lor cu ajutorul bibliotecii Jackson. Pacientii si doctorii sunt adaugati in 
liste si este setat numarul asistentelor si al investigatorilor. Desfasurarea
este organizata in runde. La inceputul fiecarei runde, un nou set de pacienti
este adus la spital. 

TRIAJ:
Pacientii sunt trecuti in coada pentru triaj, unde le este stabilit gradul de
urgenta de catre asistente in functie de tipul si severitatea afectiunii. 
Daca numarul de pacienti este mai mare decat cel de asistente, acestia raman
in coada pentru runda urmatoare. De asemenea, inainte de a fi preluati, ei 
sunt ordonati dupa nivelul de severitate.

EXAMINARE:
Pacientii care au trecut de faza de triere sunt adusi in coada de examinare.
Acestia sunt vazuti de un doctor care le poate trata afectiunea si care decide
daca pot pleca acasa doar cu tratament sau daca este nevoie de investigatii.
Daca un pacient a fost deja la investigatii, doctorul actioneaza pe baza
sugestiei tehnicianului:
	- daca este nevoie de operatie, se gaseste primul doctor chirurg. Acesta
	opereaza pacientul, scazand nivelul de severitate cu factorul stabilit,
	si il interneaza, adaugand-ul in lista sa de pacienti tinuti sub 
	observatie. De asemenea, i se calculeaza tratamentul pe care il va urma.
	- daca este nevoie doar de internare, acesta este adaugat in lista
	doctorului de pacienti tinuti sub observatie si ii este calculat
	tratamentul pe care il va urma.
	- daca poate fi trimis acasa cu tratament, este externat.
Pacientii din aceasta coada sunt sortati descrescator dupa urgenta si
severitate, si crescator alfabetic.

INVESTIGATII:
Pacientii ajunsi in aceasta coada la cererea unui doctor sunt vazuti de un
tehnician care, in functie de severitatea afectiunii, sugereaza operatie,
internare sau tratament. Ordinea in care sunt vazuti acestia este descresca-
toare dupa gradul de urgenta si severitate si crescatoare alfabetic.

APLICAREA TRATAMENTULUI ASISTENTELOR:
Asistentele aplica tratamentul prescris de doctor tuturor pacientilor
internati, scazand gradul de severitate si numarul de runde cat vor sta in
spital.

VIZITA DOCTORULUI LA PACIENTII INTERNATI:
Fiecare doctor isi va vizita toti pacientii internati si va verifica daca
si-au terminat tratamentul (au stat destule runde internati) sau daca severi-
tatea a ajuns la 0. In acest caz ei sunt externati si in caz contrar isi vor
continua tratamentul.

OBSERVER PATTERN:
In implementarea simularii a fost folosit design pattern-ul comportamental
Observer. Obiectul observat este simularea, care isi notifica observatorii
(cele trei cozi, tura asistentelor si vizitele doctorilor) in momentul in care
noi pacienti sunt primiti in spital. De asemenea, cei trei observatori sunt
la randul lor obiecte observate de catre clasele care afiseaza statistici.
Acestea sunt notificate in momentul in care apar schimbari la cei trei
subiecti (ex: sunt mutati pacientii dintr-o coada in alta, asistentele
administreaza tratamente sau doctorii externeaza pacienti).
