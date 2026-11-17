# Q5 – Four event-handling styles

Four runnable classes are provided: frame implements ActionListener, named inner class, anonymous inner class, and lambda. Each registers one handler with both the button and amount field so Enter triggers conversion. Each uses WindowAdapter for closing and shows a result/error dialog.

| Style | Strength | Trade-off |
|---|---|---|
| Frame implements listener | Simple for tiny frame | Couples frame to listener interface; handler can become large |
| Named inner class | Clear, reusable handler type | More lines and one class declaration |
| Anonymous inner class | Local and can access outer fields | Verbose; harder to reuse by name |
| Lambda | Concise for a single-method interface | Best for short behavior; can become unreadable if complex |

For a large application, prefer a separate Action class (or shared `Action`) to reuse across button, menu and keyboard shortcut. `this::convert` is a method reference that can be shared by multiple triggers. Four approaches generally create one handler object (lambda may be singleton/cached or allocated depending on capture/runtime); sharing one Action reduces duplicated handler objects and makes adding triggers straightforward. The number of registered UI bindings grows with triggers.
