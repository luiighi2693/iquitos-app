/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    TipoDeOperacionDeIngresoComponentsPage,
    TipoDeOperacionDeIngresoDeleteDialog,
    TipoDeOperacionDeIngresoUpdatePage
} from './tipo-de-operacion-de-ingreso.page-object';

const expect = chai.expect;

describe('TipoDeOperacionDeIngreso e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let tipoDeOperacionDeIngresoUpdatePage: TipoDeOperacionDeIngresoUpdatePage;
    let tipoDeOperacionDeIngresoComponentsPage: TipoDeOperacionDeIngresoComponentsPage;
    let tipoDeOperacionDeIngresoDeleteDialog: TipoDeOperacionDeIngresoDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load TipoDeOperacionDeIngresos', async () => {
        await navBarPage.goToEntity('tipo-de-operacion-de-ingreso');
        tipoDeOperacionDeIngresoComponentsPage = new TipoDeOperacionDeIngresoComponentsPage();
        expect(await tipoDeOperacionDeIngresoComponentsPage.getTitle()).to.eq('Tipo De Operacion De Ingresos');
    });

    it('should load create TipoDeOperacionDeIngreso page', async () => {
        await tipoDeOperacionDeIngresoComponentsPage.clickOnCreateButton();
        tipoDeOperacionDeIngresoUpdatePage = new TipoDeOperacionDeIngresoUpdatePage();
        expect(await tipoDeOperacionDeIngresoUpdatePage.getPageTitle()).to.eq('Create or edit a Tipo De Operacion De Ingreso');
        await tipoDeOperacionDeIngresoUpdatePage.cancel();
    });

    it('should create and save TipoDeOperacionDeIngresos', async () => {
        const nbButtonsBeforeCreate = await tipoDeOperacionDeIngresoComponentsPage.countDeleteButtons();

        await tipoDeOperacionDeIngresoComponentsPage.clickOnCreateButton();
        await promise.all([tipoDeOperacionDeIngresoUpdatePage.setNombreInput('nombre')]);
        expect(await tipoDeOperacionDeIngresoUpdatePage.getNombreInput()).to.eq('nombre');
        await tipoDeOperacionDeIngresoUpdatePage.save();
        expect(await tipoDeOperacionDeIngresoUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await tipoDeOperacionDeIngresoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last TipoDeOperacionDeIngreso', async () => {
        const nbButtonsBeforeDelete = await tipoDeOperacionDeIngresoComponentsPage.countDeleteButtons();
        await tipoDeOperacionDeIngresoComponentsPage.clickOnLastDeleteButton();

        tipoDeOperacionDeIngresoDeleteDialog = new TipoDeOperacionDeIngresoDeleteDialog();
        expect(await tipoDeOperacionDeIngresoDeleteDialog.getDialogTitle()).to.eq(
            'Are you sure you want to delete this Tipo De Operacion De Ingreso?'
        );
        await tipoDeOperacionDeIngresoDeleteDialog.clickOnConfirmButton();

        expect(await tipoDeOperacionDeIngresoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
