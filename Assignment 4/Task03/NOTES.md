# Q3 – Nested layouts and resizing

Layout tree: JFrame BorderLayout; NORTH panel BorderLayout containing title FlowLayout and search-row FlowLayout; CENTER JScrollPane containing JList; SOUTH panel BorderLayout containing action-row FlowLayout and status label. The CENTER scroll pane receives the extra space on resize; search and action rows retain preferred component sizes.

Three evidence sizes to capture locally: 700×450, 1000×650, and 500×350. The centre list should expand/shrink while the top and bottom rows remain compact. GridBagLayout is more flexible but has more constraints to maintain; absolute positioning does not adapt to resize, font scaling or localization. Nested layouts use O(c) layout work for c components and O(p) panel objects; exact timings depend on platform and component tree.
