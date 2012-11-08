#include "about_dialog.h"
#include "ui_about_dialog.h"

AboutDialog::AboutDialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::AboutDialog)
{
    ui->setupUi(this);

    QString descr =
    "<html><head/><body><p>"
    + tr("Mission list editor for")
    + " &quot;<span style=\"font-weight:600;\">"
    + tr("Horus commander")
    + "</span>&quot;, "
    + tr("which is a part of the")
    + " &quot;<a href=\"http://oblalex.github.com/horus\"><span style=\" font-weight:600; text-decoration: underline; color:#0057ae;\">"
    + tr("Horus")
    + "</span></a>&quot; "
    + tr("system")
    + ".</p></body></html>";

    QString report =
    "<html><head/><body><p>"
    +tr("Please, report any")
    +" <a href=\"https://github.com/oblalex/horus/issues\"><span style=\" text-decoration: underline; color:#0057ae;\">"
    +tr("issues")
    +"</span></a> "
    +tr("if you got some")
    +".</p></body></html>";

    setWindowTitle(tr("About"));
    ui->nameLb->setText(tr("Horus MLE"));
    ui->descrLb->setText(descr);
    ui->reportLb->setText(report);
}

AboutDialog::~AboutDialog()
{
    delete ui;
}
